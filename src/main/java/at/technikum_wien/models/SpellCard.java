package at.technikum_wien.models;

public class SpellCard extends Card{

    public SpellCard(String name, int baseDamage, Element element) {
        setName(name);
        setBaseDamage(baseDamage);
        setElement(element);
        System.out.println("Created monster card " + name + ", Damage: " + baseDamage + ", Element: " + element);
    }

    @Override
    public void Describe()
    {
        System.out.println("Spell " + getName() + ", Damage: " + getBaseDamage() + ", Element: " + getElement());
    }
}
