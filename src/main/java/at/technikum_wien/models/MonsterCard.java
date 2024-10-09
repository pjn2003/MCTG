package at.technikum_wien.models;

import lombok.Getter;
import lombok.Setter;

public class MonsterCard extends Card {

    @Getter
    @Setter
    private MonsterType type;

    public MonsterCard(String name, int baseDamage, Element element,MonsterType type) {
        setName(name);
        setBaseDamage(baseDamage);
        setElement(element);
        setType(type);
        System.out.println("Created monster card " + name + ", Damage: " + baseDamage + ", Element: " + element + ", Type: " + type);
    }

    @Override
    public void Describe()
    {
        System.out.println("Monster " + getName() + ", Damage: " + getBaseDamage() + ", Element: " + getElement() + ", Type: " + type);
    }

}
