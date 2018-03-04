/**
 * 
 */

class SpinnerView {
		
	_template(){
		return `<img src='resources/images/svg/spinner_orange.svg' />`;
	}
	
	update(){
		document.getElementById('container').innerHTML = this._template();
	}
	
	remove(){
		document.getElementById('container').innerHTML = '';
	}
}