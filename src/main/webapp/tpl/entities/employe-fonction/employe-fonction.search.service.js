(function() {
    'use strict';

    angular
        .module('app')
        .factory('EmployeFonctionSearch', EmployeFonctionSearch);

    EmployeFonctionSearch.$inject = ['$resource'];

    function EmployeFonctionSearch($resource) {
        var resourceUrl =  'api/_search/employe-fonctions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
