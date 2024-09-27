package at.technikum_wien;

public class MonsterCard extends Card {

    MonsterType type;

    public MonsterCard(String name, int baseDamage, Element element,MonsterType type) {
        this.name = name;
        this.baseDamage = baseDamage;
        this.element = element;
        this.type = type;
        System.out.println("Created monster card " + name + ", Damage: " + baseDamage + ", Element: " + element + ", Type: " + type);
    }

    @Override
    public void Describe()
    {
        System.out.println("Monster " + name + ", Damage: " + baseDamage + ", Element: " + element + ", Type: " + type);
    }

}
