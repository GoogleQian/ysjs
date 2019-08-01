package com.xiaohe.ysjspt.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author
 * Administrator
 */
@Entity
@Table(name="filter")
public class ServiceEntity implements Serializable {
    private static final long serialVersionUID = 2446493944464368L;

    /***/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(value="id")
    private Long id;
}
