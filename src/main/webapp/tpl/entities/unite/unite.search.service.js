(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .factory('UniteSearch', UniteSearch);

    UniteSearch.$inject = ['$resource'];

    function UniteSearch($resource) {
        var resourceUrl =  'api/_search/unites/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
