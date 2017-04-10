(function() {
    'use strict';

    angular
        .module('app')
        .factory('CaisseClotureSearch', CaisseClotureSearch);

    CaisseClotureSearch.$inject = ['$resource'];

    function CaisseClotureSearch($resource) {
        var resourceUrl =  'api/_search/caisse-clotures/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
