public class Player extends Creature {
    private int heal_pots = 4;

    public Player(int atk, int def, int maxHp, int minDmg, int maxDmg) {
        super(atk, def, maxHp, minDmg, maxDmg);
    }

    public int getPots() {
        return this.heal_pots;
    }

    public boolean hasPots() {
        return this.getPots() > 0;
    }

    private int calcHeal() {
        return this.maxHp() * 30 / 100;
    }

    public boolean needsHealing() {
        return this.getHp() <= this.maxHp() - this.calcHeal();
    }
    public void heal() {
        if (this.hasPots() && this.isAlive()) {
            int temp = this.getHp() + this.calcHeal();
            this.setHp(temp);
            --heal_pots;
        }
    }
}