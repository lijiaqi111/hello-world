package com.sound.haolei.model;

import java.util.Date;
import java.util.List;

public class HlHouseAdminRole extends BaseModel{
    private Integer id;

    private String name;

    private String description;

    private Date ctime;
    
    private List<HlHouseAdminModule> modules;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

	public List<HlHouseAdminModule> getModules() {
		return modules;
	}

	public void setModules(List<HlHouseAdminModule> modules) {
		this.modules = modules;
	}
    
}