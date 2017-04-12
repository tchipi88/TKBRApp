(function() {
    'use strict';
    angular
        .module('app')
        .factory('BonLivraison', BonLivraison);

    BonLivraison.$inject = ['$resource','DateUtils'];

    function BonLivraison ($resource,DateUtils) {
        var resourceUrl =  'api/bon-livraisons/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
             'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                          data.dateLivraison =DateUtils.convertLocalDateFromServer(data.dateLivraison);

                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.dateLivraison =DateUtils.convertLocalDateToServer(copy.dateLivraison);

                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.dateLivraison =DateUtils.convertLocalDateToServer(copy.dateLivraison);

                    return angular.toJson(copy);
                }
            }
        });
    }
})();
