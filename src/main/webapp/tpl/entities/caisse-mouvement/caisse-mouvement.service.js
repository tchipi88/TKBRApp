(function() {
    'use strict';
    angular
        .module('app')
        .factory('CaisseMouvement', CaisseMouvement);

    CaisseMouvement.$inject = ['$resource'];

    function CaisseMouvement ($resource) {
        var resourceUrl =  'api/caisse-mouvements/:id';

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
