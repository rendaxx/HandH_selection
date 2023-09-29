import java.util.Random;

public abstract class Creature {
    private final int atk;
    private final int def;
    private final int maxHp;
    private int curHp;
    private final int maxDmg;
    private final int minDmg;

    public Creature(int atk, int def, int maxHp, int minDmg, int maxDmg) {
        checkAttack(atk);
        this.atk = atk;

        checkDefense(def);
        this.def = def;

        checkHp(maxHp);
        this.maxHp = maxHp;
        this.curHp = maxHp;

        checkDmg(minDmg, maxDmg);
        this.maxDmg = maxDmg;
        this.minDmg = minDmg;
    }

    private void checkAttack(int atk) {
        if (atk < 1 || atk > 30) {
            throw new RuntimeException(new CreatureException.BadParametersException("Bad Attack value: " + atk));
        }
    }

    private void checkDefense(int def) {
        if (def < 1 || def > 30) {
            throw new RuntimeException(new CreatureException.BadParametersException("Bad Defense value: " + def));
        }
    }

    public void checkHp(int maxHp) {
        if (maxHp < 1) {
            throw new RuntimeException(new CreatureException.BadParametersException("Bad HP value: " + maxHp));
        }
    }

    public void checkDmg(int minDmg, int maxDmg) {
        if (minDmg > maxDmg || minDmg < 1) {
            throw new RuntimeException(
                    new CreatureException.BadParametersException(
                            "Bad Damage values: minDmg=" + minDmg + "maxDmg=" + minDmg));
        }
    }

    public int getAtk() {
        return this.atk;
    }

    public int getDef() {
        return this.def;
    }

    public int getMaxHp() {
        return this.maxHp;
    }

    public int getHp() {
        return this.curHp;
    }

    public int getDmg() {
        Random rng = new Random();
        return rng.nextInt(this.maxDmg - this.minDmg + 1) + this.minDmg;
    }

    protected void setHp(int val) {
        if (val < 0) {
            this.curHp = 0;
        } else this.curHp = Math.min(val, this.getMaxHp());
    }

    public boolean isAlive() {
        return this.getHp() > 0;
    }

    private void takeDamage(int dmg) {
        this.setHp(this.getHp() - dmg);
    }

    private int getMod(int atk, int def) {
        int mod = atk - def + 1;
        if (mod < 1) {
            mod = 1;
        }
        return mod;
    }

    private boolean isSuccess(int mod) {
        Random rng = new Random();
        while (mod > 0) {
            int roll = rng.nextInt(6) + 1;
            if (roll == 5 || roll == 6) {
                return true;
            }
            --mod;
        }
        return false;
    }

    public void hit(Creature other) {
        if (!this.isAlive()) {
            throw new RuntimeException(new CreatureException.DeadHitException("Hitting by dead " + this.getClass().getTypeName()));
        }
        int mod = getMod(this.getAtk(), other.getDef());
        if (isSuccess(mod)) {
            other.takeDamage(this.getDmg());
        }
    }
}