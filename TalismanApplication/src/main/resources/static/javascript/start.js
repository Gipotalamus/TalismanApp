window.onload = function () {
    var spans = document.getElementsByClassName('glyphicon-remove');
    for (var i = 0; i < spans.length; i++) {
        var span = spans[i];
        span.onclick = function () {
            var id = this.id;
            var xhr = new XMLHttpRequest();
            xhr.open('GET', '/talismanEvents/remove/' + id.slice(6), true);
            xhr.send();
            this.parentNode.parentNode.parentNode.remove();
        }
    }




};