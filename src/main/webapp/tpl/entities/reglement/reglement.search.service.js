(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .factory('ReglementSearch', ReglementSearch);

    ReglementSearch.$inject = ['$resource'];

    function ReglementSearch($resource) {
        var resourceUrl =  'api/_search/reglements/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
