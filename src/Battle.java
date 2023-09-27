public class Battle {
    private Player p;
    private Monster m;

    public Battle(Player p, Monster m) {
        this.p = p;
        this.m = m;
    }

    public void go() {
        while (p.isAlive() && m.isAlive()) {
            while (p.needsHealing() && p.hasPots()) {
                int temp = p.getHp();
                p.heal();
                System.out.println("Player healed " + (p.getHp() - temp) + " HP");
            }
            int temp = m.getHp();
            p.hit(m);
            System.out.println("Player did " + (temp - m.getHp()) + " damage");
            if (m.isAlive()) {
                temp = p.getHp();
                m.hit(p);
                System.out.println("Monster did " + (temp - p.getHp()) + " damage");
            }
        }
        if (p.isAlive()) {
            System.out.println("Monster wins");
        } else {
            System.out.println("Player wins");
        }
    }

    public static void main(String ... args) {
        Battle battle = new Battle(new Player(2, 2, 10, 2, 4),
                new Monster(3, 1, 8, 3, 5));
        battle.go();
    }
}
