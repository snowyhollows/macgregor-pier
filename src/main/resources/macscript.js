var state = Promise.resolve(null);
var container = document.querySelector("[data-mac-url]");
var viewRootUrl = container.dataset.macUrl;

state.then(_ => {
    return fetch(viewRootUrl);
}).then(req => {
    return req.text();
}).then(bodyText => {
    container.innerHTML = bodyText;
});

function on_event(e) {
    var source = e.srcElement;
    if (source.dataset.events && source.dataset.events.indexOf(":" + e.type) != -1) {
        var event = JSON.stringify({
            type: e.type.toUpperCase(),
            source: source.dataset.key,
            value: source.value
        });
        state.then(_ => {
            return fetch(viewRootUrl, { method: "POST", body: event, credentials: "same-origin"});
        }).then(req => {
                return req.text();
        }).then(bodyText => {
                container.innerHTML = bodyText;
        });
    }
}

container.addEventListener("click", on_event);
container.addEventListener("change", on_event);
