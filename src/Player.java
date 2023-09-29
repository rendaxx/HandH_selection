public class Player extends Creature {
    private int healPots = 4;

    public Player(int atk, int def, int maxHp, int minDmg, int maxDmg) {
        super(atk, def, maxHp, minDmg, maxDmg);
    }

    public int getPots() {
        return this.healPots;
    }

    public boolean hasPots() {
        return this.getPots() > 0;
    }

    private int calcHeal() {
        return this.getMaxHp() * 30 / 100;
    }

    public boolean needsHealing() {
        return this.getHp() <= this.getMaxHp() - this.calcHeal();
    }
    public void heal() {
        if (this.hasPots() && this.isAlive()) {
            int temp = this.getHp() + this.calcHeal();
            this.setHp(temp);
            --healPots;
        }
    }
}