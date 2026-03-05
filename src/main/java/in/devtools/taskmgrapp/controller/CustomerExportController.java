package in.devtools.taskmgrapp.controller;

import in.devtools.taskmgrapp.service.CustomerExportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class CustomerExportController {

    private final CustomerExportService customerExportService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/customers/export")
    public void exportCustomers(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=customers.xlsx");

        customerExportService.exportToExcel(response.getOutputStream());
    }
}