import java.util.Stack;

public class Tabuleiro {

    private String tabuleiro[][] = new String[3][3];
    private Stack<int[]> pilTab = new Stack<>();

    public Tabuleiro() {
        novoTabuleiro();
        imprimeTabuleiro();
    }
    
    public void novoTabuleiro() {
    	for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.tabuleiro[i][j] = " ";
            }
        }
        this.pilTab = new Stack<>();
    }

    public String[][] getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(String[][] tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public Stack<int[]> getPilTab() {
		return pilTab;
	}

	public void setPilTab(Stack<int[]> pilTab) {
		this.pilTab = pilTab;
	}

	public void imprimeTabuleiro() {
        System.out.println("   1  2  3");
        for (int i = 0; i < 3; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 3; j++) {
                System.out.print("[" + this.tabuleiro[i][j] + "]");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public boolean verificaJogada(int l, int c) {
        if (this.tabuleiro[l][c].equals(" ") && l >= 0 && c >= 0 && l <= 2 && c <= 2) {
            return true;
        } else {
            return false;
        }
    }

    public void setJogada(int l, int c, String jogada) {
        this.tabuleiro[l][c] = jogada;
        imprimeTabuleiro();
        this.pilTab.push(new int[]{l, c});
    }

    public void volta(int x) {
    	for (int i = 0; i < x; i++) {
			this.tabuleiro[this.pilTab.peek()[0]][this.pilTab.pop()[1]] = " ";
		}
    	imprimeTabuleiro();
    }
    
    public boolean venceu(Jogador jog) {
        for (int i = 0; i < 3; i++) {
            int x = 0;
            int y = 0;
            for (int j = 0; j < 3; j++) {
                if (!this.tabuleiro[i][j].equals(" ")) {
                    if (this.tabuleiro[i][j].equals(jog.getSimbolo())) {
                        x += 1;
                    }
                }
                if (!this.tabuleiro[j][i].equals(" ")) {
                    if (this.tabuleiro[j][i].equals(jog.getSimbolo())) {
                        y += 1;
                    }
                }
                if (x == 3 || y == 3) {
                    return true;
                }
            }
            if (this.tabuleiro[0][0].equals(this.tabuleiro[1][1]) && this.tabuleiro[0][0].equals(this.tabuleiro[2][2]) && !this.tabuleiro[0][0].equals(" ") && !this.tabuleiro[1][1].equals(" ")) {
                return true;
            }
            if (this.tabuleiro[0][2].equals(this.tabuleiro[1][1]) && this.tabuleiro[0][2].equals(this.tabuleiro[2][0]) && !this.tabuleiro[0][2].equals(" ") && !this.tabuleiro[1][1].equals(" ")) {
                return true;
            }

        }
        return false;
    }


}
