package cygnus.platform.metric.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "metric")
public class Metric {

    @Id
    private String id;
    private String description;
}
