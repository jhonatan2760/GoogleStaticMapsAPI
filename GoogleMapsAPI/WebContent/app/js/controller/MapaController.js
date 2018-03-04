/**
 * 
 */

class MapaController {
	
	constructor(){
		let $ = document.querySelector.bind(document);
		this._pontoPartida = $("#enderecoPartida");
		this._pontoChegada = $("#enderecoChegada");
		this._img = document.getElementById('img');
	}
	
	carregarMapa(event){
		event.preventDefault();
		let p1, p2;  
		p1 = this._pontoPartida.value;
		p2 = this._pontoChegada.value;
		if(p1.trim() != "" && p2.trim() != ""){
			let mapa = new Mapa(p1, p2);
			this._criarConexaoAjax(mapa);
		}else{
			alert('Preencha os campos!');
		}
		
	}
	
	_criarConexaoAjax(mapa){
		let sp = new SpinnerView();
		sp.update();
		let endPoint = `Maps?init=${mapa.pontoPartida}&origin=${mapa.pontoChegada}`;
		let xmlReq = new XMLHttpRequest();
		let mp = new MapaView({});
		xmlReq.onreadystatechange = function(){
			if(xmlReq.readyState === 4 )		
				if(xmlReq.status === 200){
					let json = JSON.parse(xmlReq.responseText);
					mp.update(json);				
				}else if(xmlReq.status === 500){
					mp.error(xmlReq);
				}
		}
		xmlReq.open('GET', endPoint);
		xmlReq.send();
	}
	
	
}
