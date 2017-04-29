(function() {
    'use strict';
    angular
        .module('app')
        .factory('TerrainCommande', TerrainCommande);

    TerrainCommande.$inject = ['$resource','DateUtils'];

    function TerrainCommande ($resource,DateUtils) {
        var resourceUrl =  'api/terrain-commandes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
             'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                          data.dateEmission =DateUtils.convertLocalDateFromServer(data.dateEmission);

                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.dateEmission =DateUtils.convertLocalDateToServer(copy.dateEmission);

                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.dateEmission =DateUtils.convertLocalDateToServer(copy.dateEmission);

                    return angular.toJson(copy);
                }
            }
        });
    }
})();
