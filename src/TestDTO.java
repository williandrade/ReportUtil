
import me.williandrade.annotation.XlsxSheet;
import me.williandrade.annotation.XlsxField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author williandrade.me
 */
@XlsxSheet()
public class TestDTO {

    @XlsxField(title = "Nome")
    private String name;
    @XlsxField(title = "Idade")
    private int age;
    @XlsxField(title = "Endereço de E-mail")
    private String mainAddress;
    @XlsxField(title = "Salário", cellType = XlsxField.CellType.CURRENCY)
    private Double salary;
    @XlsxField(title = "Altura")
    private Long height;

    public TestDTO(String name, int age, String mainAddress, Double salary, Long height) {
        this.name = name;
        this.age = age;
        this.mainAddress = mainAddress;
        this.salary = salary;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(String mainAddress) {
        this.mainAddress = mainAddress;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

}
