package at.technikum_wien;

abstract class Card {

    String name;
    int baseDamage;
    Element element;


    public void Describe()
    {
        System.out.println("This is a card.");
    }



    public void setName(String name) {
        this.name = name;
    }
    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }
    public void setElement(Element element) {
        this.element = element;
    }
    public String getName() {
        return name;
    }
    public int getBaseDamage() {
        return baseDamage;
    }
    public Element getElement() {
        return element;
    }



}
