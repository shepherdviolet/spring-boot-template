package template.basic.entity;

/**
 * 测试请求实例
 *
 * @author S.Violet
 */
public class TestRequest {

//    @NotBlank(message = "id is blank")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
