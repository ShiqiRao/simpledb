package com.dzion.simpledb;

import lombok.Data;

/**
 * @ClassName Table
 * @Author Rao Shiqi
 * @Date 2019/5/31 11:39
 * @Description TODO
 */
@Data
public class Table {
    private DbFile file;
    private String name;
    private String primaryKeyName;

    public Table(DbFile file, String name, String pkeyField) {
        this.file = file;
        this.name = name;
        this.primaryKeyName = pkeyField;
    }
}
