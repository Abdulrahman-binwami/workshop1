function searchAndHighlight() {
    removeHighlights();
    let searchTerm = document.getElementById('searchInput').value.trim();
    if (!searchTerm) return;
    let searchRegex = new RegExp(searchTerm, 'gi');
    let content = document.getElementById('content');
    let nodes = content.childNodes;
    nodes.forEach(function(node) {
        if (node.nodeType === 3) {
            let match;
            let wholeText = node.nodeValue;
            let pos = 0;
            while ((match = searchRegex.exec(wholeText)) !== null) {
                let span = document.createElement('span');
                span.className = 'highlight';
                let matchedText = document.createTextNode(match[0]);
                span.appendChild(matchedText);
                node.parentNode.insertBefore(span, node.splitText(match.index));
                node = node.splitText(match.index + match[0].length);
                pos = match.index + match[0].length;
            }
        }
    });
}

function removeHighlights() {
    let highlights = document.querySelectorAll('.highlight');
    highlights.forEach(function (highlight) {
        let parent = highlight.parentNode;
        parent.replaceChild(highlight.firstChild, highlight);
        parent.normalize();
    });
}

function showSuggestions(value) {
    let suggestions = [
        "Web design",
        "Graphic design",
        "HTML",
        "CSS",
        "JavaScript",
        "PHP",
        "Network troubleshooting",
        "Hardware troubleshooting",
        "IT maintenance",
        "Software development"
    ];
    let suggestionsBox = document.getElementById('suggestions');
    suggestionsBox.innerHTML = '';
    if (value) {
        let filteredSuggestions = suggestions.filter(suggestion => suggestion.toLowerCase().includes(value.toLowerCase()));
        filteredSuggestions.forEach(function (suggestion) {
            let li = document.createElement('li');
            li.textContent = suggestion;
            li.onclick = function () {
                document.getElementById('searchInput').value = suggestion;
                suggestionsBox.innerHTML = '';
                searchAndHighlight();
            };
            suggestionsBox.appendChild(li);
        });
    }
}
