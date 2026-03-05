package in.devtools.taskmgrapp.dto;

import in.devtools.taskmgrapp.entity.enums.CustomerStatus;

public record CustomerExportRow(String name, String email, Long mobile, CustomerStatus status) {}