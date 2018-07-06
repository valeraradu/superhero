package superhero.enums;

import java.util.HashMap;
import java.util.Map;

public enum Skill {

    FLY("fly"),
    TELEPORT("teleport"),
    THROW_FLAME("throw flame");

    private static Map<String, Skill> skillMap = new HashMap<String, Skill>();

    static {
        for (Skill skill : Skill.values()) {
            skillMap.put(skill.description, skill);
        }
    }

    private String description;

    Skill(String description) {
        this.description = description;
    }

    public static Skill lookup(String key) {
        return skillMap.get(key);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
