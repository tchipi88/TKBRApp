(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('CommandeDetailController', CommandeDetailController);

    CommandeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Commande', 'Client', 'Fournisseur', 'CommandeLigne', 'Reglement'];

    function CommandeDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Commande, Client, Fournisseur, CommandeLigne, Reglement) {
        var vm = this;

        vm.commande = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:commandeUpdate', function(event, result) {
            vm.commande = result;
        });
        $scope.$on('$destroy', unsubscribe);
        
        
        
        loadAll();

        function loadAll () {
            {
                CommandeLigne.query({
                }, onSuccess, onError);
            }
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.commandeLignes = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
    }
})();
