package group.artifia.model;

import lombok.Data;
//lombok插件要检查
@Data
public class Userinfo {
    int user_id;                        //和数据库中的一致
    String user_name;
    String user_pass;
    int user_rank;
}
