(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .factory('PvReceptionSearch', PvReceptionSearch);

    PvReceptionSearch.$inject = ['$resource'];

    function PvReceptionSearch($resource) {
        var resourceUrl =  'api/_search/pv-receptions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
