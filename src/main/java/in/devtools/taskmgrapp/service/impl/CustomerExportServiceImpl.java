package in.devtools.taskmgrapp.service.impl;

import in.devtools.taskmgrapp.dto.CustomerExportRow;
import in.devtools.taskmgrapp.repository.CustomerRepository;
import in.devtools.taskmgrapp.service.CustomerExportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerExportServiceImpl implements CustomerExportService {

    private static final int CHUNK_SIZE = 500;

    private final CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public void exportToExcel(OutputStream out) throws IOException {
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(100)) {
            Sheet sheet = workbook.createSheet("Customers");

            // Header row
            CellStyle headerStyle = buildHeaderStyle(workbook);
            Row header = sheet.createRow(0);
            String[] cols = {"#", "Name", "Email", "Mobile", "Status"};
            for (int i = 0; i < cols.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(cols[i]);
                cell.setCellStyle(headerStyle);
            }

            // Chunked data rows
            int rowNum = 1;
            int pageNum = 0;
            Slice<CustomerExportRow> slice;

            do {
                slice = customerRepository.findExportSlice(PageRequest.of(pageNum, CHUNK_SIZE));

                for (CustomerExportRow c : slice.getContent()) {
                    Row row = sheet.createRow(rowNum);
                    row.createCell(0).setCellValue(rowNum);
                    row.createCell(1).setCellValue(c.name() != null ? c.name() : "");
                    row.createCell(2).setCellValue(c.email() != null ? c.email() : "");
                    row.createCell(3).setCellValue(c.mobile() != null ? c.mobile().toString() : "");

                    String statusVal = c.status() != null ? c.status().name() : "";
                    row.createCell(4).setCellValue(statusVal);

                    rowNum++;
                }

                log.info("[Export] Written {} rows. Page {} done.", rowNum - 1, pageNum);
                pageNum++;

            } while (slice.hasNext());

            workbook.write(out);
            workbook.dispose();
            log.info("[Export] Excel export complete. Total rows: {}", rowNum - 1);
        }
    }

    private CellStyle buildHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }
}