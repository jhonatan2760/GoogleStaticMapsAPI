/**
 * Mapa Model
 * @author Jhonatan S. Souza http://www.jhonatansouza.com
 */

class Mapa {
	
	constructor(p1="Golden Gate Bridge San Francisco CA", p2 = "Ridgefield Avenue Daly City CA"){
		this._pontoPartida = p1;
		this._pontoChegada = p2	;
		Object.freeze(this);
	}
	
	get pontoPartida(){
		return this._pontoPartida;
	}
	
	get pontoChegada(){
		return this._pontoChegada;
	}
	
}
