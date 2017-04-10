(function() {
    'use strict';
    angular
        .module('app')
        .factory('CommandeLigne', CommandeLigne);

    CommandeLigne.$inject = ['$resource'];

    function CommandeLigne ($resource) {
        var resourceUrl =  'api/commande-lignes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
