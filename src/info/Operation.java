package info;

import java.io.Serializable;

public interface Operation extends Serializable {
    Result execute(RegUsers users);

}