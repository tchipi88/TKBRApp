(function() {
    'use strict';

    angular
        .module('app')
        .factory('BonReceptionSearch', BonReceptionSearch);

    BonReceptionSearch.$inject = ['$resource'];

    function BonReceptionSearch($resource) {
        var resourceUrl =  'api/_search/bon-receptions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
