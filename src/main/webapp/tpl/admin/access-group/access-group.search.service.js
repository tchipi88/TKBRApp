(function() {
    'use strict';

    angular
        .module('app')
        .factory('AccessGroupSearch', AccessGroupSearch);

    AccessGroupSearch.$inject = ['$resource'];

    function AccessGroupSearch($resource) {
        var resourceUrl =  'api/_search/access-groups/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
