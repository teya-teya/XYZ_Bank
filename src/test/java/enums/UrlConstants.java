package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UrlConstants {
    MANAGER("manager", "Страница менеджера");

    private final String value;
    private final String description;
}
