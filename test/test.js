//
// Top level test for OurGame
//
var assert = require('assert');

describe('simple test', function(){
    it('works', function() {
        assert.equal('a', 'a');
    });
    it('fails gracefully', function(){
        assert.throws(function(){
            throw 'Error!';
        });
    });
});

describe('simple async test', function() {
  it('timeout works', function(done) {
    setTimeout(function() {
      done();
    }, 25);
  });
});