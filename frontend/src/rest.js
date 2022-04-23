(function (factory) {
    if (typeof module === "object" && typeof module.exports === "object") {
        var v = factory(require, exports);
        if (v !== undefined) module.exports = v;
    }
    else if (typeof define === "function" && define.amd) {
        define(["require", "exports"], factory);
    }
})(function (require, exports) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    exports.get_answers = void 0;
    var get_answers = function (query) {
        console.log("hello in function");
        return ["hello", "bye", query];
    };
    exports.get_answers = get_answers;
});
//52.236.88.159:443/note?q=tekstdowyszukania
//# sourceMappingURL=rest.js.map