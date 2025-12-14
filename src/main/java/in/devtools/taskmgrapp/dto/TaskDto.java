package in.devtools.taskmgrapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TaskDto {

    private Long id;
    private String name;
    private String description;
    @DateTimeFormat(pattern = "yyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyy-MM-dd")
    private Date endDate;
    private String priority;
    private String status;

}
