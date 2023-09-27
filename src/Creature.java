import java.util.Random;

public abstract class Creature {
    private final int atk;
    private final int def;
    private final int maxHp;
    private int curHp;
    private final int maxDmg;
    private final int minDmg;

    public Creature(int atk, int def, int maxHp, int minDmg, int maxDmg) {
        if (atk < 1 || atk > 30) {
            try {
                throw new CreatureException.BadParametersException("Bad Attack value: " + atk);
            } catch (CreatureException.BadParametersException e) {
                throw new RuntimeException(e);
            }
        }
        this.atk = atk;
        if (def < 1 || def > 30) {
            try {
                throw new CreatureException.BadParametersException("Bad Defense value: " + def);
            } catch (CreatureException.BadParametersException e) {
                throw new RuntimeException(e);
            }
        }
        this.def = def;
        if (maxHp < 1) {
            try {
                throw new CreatureException.BadParametersException("Bad HP value: " + maxHp);
            } catch (CreatureException.BadParametersException e) {
                throw new RuntimeException(e);
            }
        }
        this.maxHp = maxHp;
        this.curHp = maxHp;
        if (minDmg > maxDmg || minDmg < 1) {
            try {
                throw new CreatureException.BadParametersException("Bad Damage values: minDmg=" + minDmg + "maxDmg=" + minDmg);
            } catch (CreatureException.BadParametersException e) {
                throw new RuntimeException(e);
            }
        }
        this.maxDmg = maxDmg;
        this.minDmg = minDmg;
    }

    public int getAtk() {
        return this.atk;
    }

    public int getDef() {
        return this.def;
    }

    public int maxHp() {
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
        } else this.curHp = Math.min(val, this.maxHp());
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
            try {
                throw new CreatureException.DeadHitException("Hitting by dead " + this.getClass().getTypeName());
            } catch (CreatureException.DeadHitException e) {
                throw new RuntimeException(e);
            }
        }
        int mod = getMod(this.getAtk(), other.getDef());
        if (isSuccess(mod)) {
            other.takeDamage(this.getDmg());
        }
    }
}