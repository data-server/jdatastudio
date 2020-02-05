package com.dataserver.api.schema;

import com.dataserver.api.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import net.sf.cglib.core.CollectionUtils;
import org.mongodb.morphia.annotations.Embedded;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gongxinyi on 2018-10-15.
 */
@Data
@Builder
@Embedded
public class Field {
    protected String id;
    protected String name;

    protected String label;
    protected Integer maxLength;
    protected boolean required;
    protected String defaultValue;
    private boolean autoIncremented;
    private boolean partOfPrimaryKey;

    private JDBCType jdbcType;
    private DbColumnType dbColumnType;

    ///////////////////////////jdatastudio 属性/////////////////////////////
    private ComponentType component;
    private boolean showInList;
    private boolean showInShow;
    private boolean showInEdit;
    private boolean showInCreate;
    private boolean showInFilter;
    private boolean alwaysOn;
    private boolean sortable;

    /**
     * 引用字段的属性
     */
    private String reference;
    private String referenceOptionText;
    /**
     * 脱敏类型
     */
    private SensitiveEnum sensitiveType;

    /**
     * 选项值
     */
    private List<ChoiceItem> choices;

    /**
     * 多值过滤
     */
    private boolean multiFilter;

    /**
     * 前端设置下拉选项值
     *
     * @return
     */
    public String getChoicesStr() {
        return choices == null ? "":choices.stream().map(choiceItem ->
                choiceItem.getId() + "|" + choiceItem.getName()
        ).collect(Collectors.joining(Constants.choiceitemtab));
    }

    public void setChoicesStr(String choicesStr){
        choices = new ArrayList<>();
        String[] choicesArray =  choicesStr.split(Constants.choiceitemtab);
        for(String choice:choicesArray){
            String[] choiceItems = choice.split(Constants.choiceitemdelimiter);
            if(choiceItems.length==2){
                choices.add(new ChoiceItem(choiceItems[0],choiceItems[1]));
            }
        }
    }

    /**
     * 是否主字段
     */
    private boolean mainField;

    /**
     * 解决mysql字段名称包含`字符的问题
     *
     * @return
     */
    @JsonProperty("name")
    public String getName() {
        if (name == null) {
            return "";
        } else {
            return name.replace("`", "");
        }
    }

    @Tolerate
    public Field() {
    }
}
