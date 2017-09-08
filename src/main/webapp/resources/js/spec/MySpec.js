describe("Test common", function() {
 var imageViewer = new IV();
 
 it("url", function() {	 
	   expect('/Earth/abc').toBe(IV.url('abc'));
	 });
 
 it("rotateRectangle", function() {	 
	   expect([100,50]).toEqual(imageViewer.rotateRectangle(50,100,90));
	 });
});