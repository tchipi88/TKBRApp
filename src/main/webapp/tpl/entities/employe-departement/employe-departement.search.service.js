(function() {
    'use strict';

    angular
        .module('app')
        .factory('EmployeDepartementSearch', EmployeDepartementSearch);

    EmployeDepartementSearch.$inject = ['$resource'];

    function EmployeDepartementSearch($resource) {
        var resourceUrl =  'api/_search/employe-departements/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
