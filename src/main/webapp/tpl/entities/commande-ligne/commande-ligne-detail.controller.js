(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('CommandeLigneDetailController', CommandeLigneDetailController);

    CommandeLigneDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CommandeLigne', 'Commande', 'Produit'];

    function CommandeLigneDetailController($scope, $rootScope, $stateParams, previousState, entity, CommandeLigne, Commande, Produit) {
        var vm = this;

        vm.commandeLigne = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tkbrApp:commandeLigneUpdate', function(event, result) {
            vm.commandeLigne = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
