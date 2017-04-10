(function() {
    'use strict';

    angular
        .module('app')
        .factory('AccessSearch', AccessSearch);

    AccessSearch.$inject = ['$resource'];

    function AccessSearch($resource) {
        var resourceUrl =  'api/_search/accesss/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
