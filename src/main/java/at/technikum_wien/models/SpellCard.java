package at.technikum_wien.models;

public class SpellCard extends Card{

    public SpellCard(int id, String name, int baseDamage, Element element) {
        setId(id);
        setName(name);
        setBaseDamage(baseDamage);
        setElement(element);
        System.out.println("Created monster card " + name + ", Damage: " + baseDamage + ", Element: " + element);
    }

    @Override
    public void describe()
    {
        System.out.println("Spell " + getName() + ", Damage: " + getBaseDamage() + ", Element: " + getElement());
    }
}
