package in.devtools.taskmgrapp.service;

import java.io.IOException;
import java.io.OutputStream;

public interface CustomerExportService {
    void exportToExcel(OutputStream out) throws IOException;
}