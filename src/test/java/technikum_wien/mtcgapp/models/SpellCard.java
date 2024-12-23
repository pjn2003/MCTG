package technikum_wien.mtcgapp.models;

public class SpellCard extends Card {

    public SpellCard(int id, String name, int baseDamage, Element element) {
        setId(id);
        setName(name);
        setBaseDamage(baseDamage);
        setElement(element);
        //System.out.println("Created spell card " + name + ", Damage: " + baseDamage + ", Element: " + element);
    }

    @Override
    public String describe()
    {
        System.out.println("Spell " + getName() + ", Damage: " + getBaseDamage() + ", Element: " + getElement());
        return null;
    }
}
