(function() {
    'use strict';

    angular
        .module('app')
        .controller('CaisseClotureDeleteController',CaisseClotureDeleteController);

    CaisseClotureDeleteController.$inject = ['$uibModalInstance', 'entity', 'CaisseCloture'];

    function CaisseClotureDeleteController($uibModalInstance, entity, CaisseCloture) {
        var vm = this;

        vm.caisseCloture = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CaisseCloture.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
