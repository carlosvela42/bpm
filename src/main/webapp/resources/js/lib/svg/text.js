SVG.Element.prototype.draw.extend('text', {
	init:function(e){
		var p = this.startPoint;
		this.el.attr({ x: p.x, y: p.y});
	},
	calc:function (e) {
		var text = {
                x: this.startPoint.x,
                y: this.startPoint.y
            },  p = this.transformPoint(e.clientX, e.clientY);
		this.snapToGrid(text);
		this.el.attr(text);
	}
});