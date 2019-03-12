package DTO;

import java.io.Serializable;

/**
 *
 * @author Absalom Herrera
 */
public class Option implements Serializable {

    private int option;

    public Option() {
    }

    public Option(int option) {
        this.option = option;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return "Option{" + "option=" + option + '}';
    }

}
