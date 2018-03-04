/**
 * 
 */

class MapaView {
		
	constructor(element){
		this._element = element;
		Object.freeze(this);
	}

	_generateLoading(){
		return `<img src='../resources/images/svg/spinner.svg' />`;
	}
	
	_template(json){
		return `<img src='data:image/png;base64,${json.image}' id='mapa' />
			<ul class='list-group'>
				${json.instructions.map( (i) =>`<li class='list-group-item'>${i.instruction}</li>` ).join('')}
			</ul>
			`;
	}
	
	error(json){
		document.getElementById('container').innerHTML = "Verifique os parametros e tente novamente!";
	}
	
	update(json){
		document.getElementById('container').innerHTML = this._template(json);
	}
	
}