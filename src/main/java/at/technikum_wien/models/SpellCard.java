package at.technikum_wien.models;

public class SpellCard extends Card{

    public SpellCard(String name, int baseDamage, Element element) {
        this.name = name;
        this.baseDamage = baseDamage;
        this.element = element;
        System.out.println("Created monster card " + name + ", Damage: " + baseDamage + ", Element: " + element);
    }

    @Override
    public void Describe()
    {
        System.out.println("Spell " + name + ", Damage: " + baseDamage + ", Element: " + element);
    }
}
